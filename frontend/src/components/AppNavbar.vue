<template>
  <nav class="navbar">
    <div class="navbar-brand">
      <span class="navbar-title">Table<strong>Select</strong></span>
    </div>
    <div class="navbar-meal">
      <span class="meal-name">Todays daily meal: {{ meal }}</span>
      <img v-if="mealUrl" :src="mealUrl" alt="Daily Meal" class="meal-image" />
    </div>
  </nav>
</template>

<script>
export default {
  name: 'AppNavbar',
  data() {
    return {
      meal: '',
      mealUrl: '',
    }
  },
  mounted() {
    fetch('http://localhost:8080/api/daily-meal')
      .then((response) => response.json())
      .then((data) => {
        this.meal = data.strMeal
        this.mealUrl = data.strMealThumb + '/small'
        console.log(data)
      })
      .catch((error) => console.error('Error fetching meal:', error))
  },
}
</script>

<style scoped>
.navbar {
  height: 60px;
  background: #2d2d2d;
  border-bottom: 1px solid #e0d8cc;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
}

.navbar-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 22px;
  color: #ffffff;
}
.navbar-meal {
  display: flex;
  align-items: center;
  gap: 12px;
}
.meal-name {
  font-size: 14px;
  color: #ffffff;
}
.meal-image {
  width: 32px;
  height: 32px;
  border-radius: 4px;
  object-fit: cover;
}
</style>
