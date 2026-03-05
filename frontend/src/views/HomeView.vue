<template>
    <div>
        <FilterPanel @search="handleSearch" />
        <FloorPlan :tables="allTables" :recommended="recommendedTables"/>
    </div>
</template>

<script>
import FilterPanel from '@/components/FilterPanel.vue';
import FloorPlan from '@/components/FloorPlan.vue';

export default {
  components: {
    FilterPanel,
    FloorPlan
  },
  data() {
    return {
      allTables: [],
      recommendedTables: [] 
    };
  },
  mounted() {
    fetch('http://localhost:8080/api/table')
      .then(response => response.json())
      .then(data => {
        this.allTables = data;
      })
      .catch(error => console.error('Error fetching tables:', error));
  },
  methods: {
    handleSearch(filters) {
      const params=new URLSearchParams({
        numberOfPeople: filters.numberOfPeople,
        zone: filters.zone,
        window: filters.window,
        quiet: filters.quiet,
        dateTime: filters.dateTime,
        durationHours: filters.durationHours
      })
      fetch(`http://localhost:8080/api/table/recommend?${params.toString()}`)
        .then(response => response.json())
        .then(data => {
          this.recommendedTables = data;
        })
        .catch(error => console.error('Error fetching recommendations:', error));
    
        }
    }
}
</script>