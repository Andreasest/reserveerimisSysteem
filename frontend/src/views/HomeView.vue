<template>
  <div class="app">
    <AppNavbar />
    <div class="content">
      <FilterPanel @search="handleSearch" />
      <div class="floor-plan-wrapper">
        <FloorPlan
          :tables="allTables"
          :recommended="recommendedTables"
          :availableTables="availableTableIds"
          :filters="currentFilters"
          @reservationMade="fetchTables"
        />
      </div>
    </div>
  </div>
</template>

<script>
import FilterPanel from '@/components/FilterPanel.vue'
import FloorPlan from '@/components/FloorPlan.vue'
import AppNavbar from '@/components/AppNavbar.vue'

export default {
  components: {
    FilterPanel,
    FloorPlan,
    AppNavbar,
  },
  data() {
    return {
      allTables: [],
      recommendedTables: [],
      availableTableIds: [],
      currentFilters: {
        numberOfPeople: 4,
        dateTime: '',
        zone: '',
        window: false,
        quiet: false,
        durationHours: 2,
      },
    }
  },
  mounted() {
    // Fetch all tables on mount
    this.fetchTables()
  },
  methods: {
    fetchTables() {
      // Fetch all tables from the backend
      fetch('http://localhost:8080/api/table')
        .then((response) => response.json())
        .then((data) => {
          this.allTables = data
        })
        .catch((error) => console.error('Error fetching tables:', error))
    },
    handleSearch(filters) {
      // Handle search from FilterPanel
      this.currentFilters = filters

      const params = new URLSearchParams({
        numberOfPeople: filters.numberOfPeople,
        zone: filters.zone,
        window: filters.window,
        quiet: filters.quiet,
        dateTime: filters.dateTime,
        durationHours: filters.durationHours,
      })
      fetch(`http://localhost:8080/api/table/recommend?${params.toString()}`) // Fetches recommended table(s)
        .then((response) => response.json())
        .then((data) => {
          this.recommendedTables = data
        })
        .catch((error) => console.error('Error fetching recommendations:', error))

      fetch(
        `http://localhost:8080/api/reservation/available?dateTime=${filters.dateTime}&durationHours=2`,
      ) // Fetches available tables for the selected date and time
        .then((res) => res.json())
        .then((data) => (this.availableTableIds = data))
        .catch((error) => console.error('Error fetching available tables:', error))
    },
  },
}
</script>

<style scoped>
.app {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
}

.content {
  display: flex;
  flex: 1;
  margin-top: 60px;
  overflow: hidden;
  align-items: center;
  background: #e8e0d0;
}

.floor-plan-wrapper {
  flex: 1;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
</style>
