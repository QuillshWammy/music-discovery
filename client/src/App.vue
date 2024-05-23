<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
        <RouterLink to="/form">Form</RouterLink>
      </nav>
    </div>
  </header>

  <main>
    <div class="container">
      <h1>Music Discovery</h1>
      <RouterView />
    </div>
  </main>

  <footer>
    <p>&copy; 2023 Music Discovery. All rights reserved.</p>
  </footer>
</template>

<style scoped>
header {
  background-color: #8b4513;
  padding: 1rem;
}

nav {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

nav a {
  color: #f5deb3;
  font-size: 1.2rem;
  text-decoration: none;
}

nav a:hover {
  text-decoration: underline;
}

main {
  padding: 2rem 0;
}

.container {
  max-width: 960px;
  margin: 0 auto;
  padding: 0 1rem;
}

h1 {
  color: #8b4513;
  font-size: 3rem;
  text-align: center;
  margin-bottom: 2rem;
}

footer {
  background-color: #8b4513;
  color: #f5deb3;
  padding: 1rem;
  text-align: center;
}
</style>
