// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  typescript: {
    strict: true,
    shim: true
  },
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },
  vite: {
    server: {
      proxy: {
        '/api': {
          target: "http://localhost:8081",
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        },
      },
    },
  },
});
