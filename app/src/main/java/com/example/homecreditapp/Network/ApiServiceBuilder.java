package com.example.homecreditapp.Network;

import android.content.Context;

import com.example.homecreditapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceBuilder {

    private static final String TAG = "ApiServiceBuilder";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String HTTP_CACHE = "http-cache";
    private static final String PRAGMA = "Pragma";
    private static final String KEY_API_URL = BuildConfig.BASE_URL ;
    private static final int CACHE_SIZE = 10 * 1024 * 2014;

    public ApiServiceBuilder() {

    }

    public ApiService provideApiService() {
        return provideRetrofit(KEY_API_URL).create(ApiService.class);
    }

    public ApiService provideApiServiceUrl(String baseUrl) {
        return provideRetrofit(baseUrl).create(ApiService.class);
    }

    public ApiService provideApiServiceWithCache(Context context) {
        return provideRetrofitWithCache(context, KEY_API_URL).create(ApiService.class);
    }

    private OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();

        return httpClient
                .addInterceptor(provideInterceptor())
                .addInterceptor(provideInterceptorWithHttpLogging())
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS).build();
    }

    private Retrofit provideRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.client(getUnsafeOkHttpClient().build())
                .client(provideOkHttpClient())
                .build();
    }

    private Retrofit provideRetrofitWithCache(Context context, String apiUrl) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(provideInterceptor())
                .addInterceptor(provideInterceptorWithHttpLogging())
                .addInterceptor(provideInterceptorWithOfflineCache(context))
                .addNetworkInterceptor(provideInterceptorWithCache())
                .cache(provideCache(context))
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Interceptor provideInterceptor() {
        return (Interceptor.Chain chain) -> {
            Request originalRequest = chain.request();
            String url = originalRequest.url().toString();
            url = url.replace("%3D", "=");
            Request request = originalRequest.newBuilder().url(url).build();
            return chain.proceed(request);
        };
    }

    private HttpLoggingInterceptor provideInterceptorWithHttpLogging() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private Interceptor provideInterceptorWithCache() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(15, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .removeHeader(PRAGMA)
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private Interceptor provideInterceptorWithOfflineCache(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!NetworkUtil.hasNetwork(context)) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(15, TimeUnit.SECONDS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(PRAGMA)
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    private Cache provideCache(Context context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getApplicationContext().getCacheDir(), HTTP_CACHE),
                    CACHE_SIZE);
        } catch (Exception e) {
        }
        return cache;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
