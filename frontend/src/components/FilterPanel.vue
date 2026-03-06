<template>
  <div class="filter-panel">
    <form @submit.prevent="submitSearch">
      <div class="form-group">
        <div class="field">
          <label for="dateTime">Select Date & Time</label>
          <input type="datetime-local" id="dateTime" v-model="filters.dateTime" required />
        </div>

        <div class="field">
          <label for="numOfPeople">Party Size</label>
          <div class="party-size-row">
            <button type="button" @click="decrement">−</button>
            <span>{{ filters.numberOfPeople }} Guests</span>
            <button type="button" @click="increment">+</button>
          </div>
        </div>

        <div class="field">
          <label for="zone">Zone Preference</label>
          <select id="zone" v-model="filters.zone">
            <option value="">All Zones</option>
            <option value="MAIN">Main Hall</option>
            <option value="PRIVATE">Private Rooms</option>
            <option value="TERRACE">Terrace</option>
          </select>
        </div>

        <div class="field">
          <label>Seating Preferences</label>
          <div class="checkboxes">
            <label class="checkbox-label">
              <input type="checkbox" id="window" v-model="filters.window" />
              Window Seat
            </label>
            <label class="checkbox-label">
              <input type="checkbox" id="quiet" v-model="filters.quiet" />
              Quiet Area
            </label>
          </div>
        </div>

        <button type="submit" class="find-btn">Find Available Tables</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'FilterPanel',
  data() {
    return {
      filters: {
        numberOfPeople: 4,
        zone: '',
        window: false,
        quiet: false,
        dateTime: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
          .toISOString()
          .slice(0, 16),
        durationHours: 2,
      },
    }
  },
  methods: {
    submitSearch() {
      this.$emit('search', this.filters)
    },
    increment() {
      //Increments the number of people
      if (this.filters.numberOfPeople < 20) this.filters.numberOfPeople++
    },
    decrement() {
      //Decrements the number of people
      if (this.filters.numberOfPeople > 1) this.filters.numberOfPeople--
    },
  },
  mounted() {
    // Emits initial search on mount
    this.$emit('search', this.filters)
  },
}
</script>

<style scoped>
.filter-panel {
  width: 280px;
  min-width: 280px;
  height: calc(100vh - 60px);
  background: #f5f0e8;
  padding: 24px 20px;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #e0d8cc;
  overflow-y: auto;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 20px;
  height: 100%;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

label {
  font-size: 14px;
  font-weight: 600;
  color: #2d2d2d;
}

input[type='datetime-local'],
select {
  padding: 8px 12px;
  border-radius: 8px;
  border: 1px solid #ddd;
  background: #fff;
  font-size: 14px;
  color: #2d2d2d;
  width: 100%;
  cursor: pointer;
}

.party-size-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 6px 12px;
}

.party-size-row button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #2d2d2d;
  padding: 0 6px;
  margin: 0;
  transition: background 0.2s;
}

button {
  border-radius: 5px;
}

button:hover {
  background: #ededed;
}

.party-size-row span {
  font-size: 14px;
  color: #2d2d2d;
}

.checkboxes {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 400;
  font-size: 13px;
  color: #2d2d2d;
  cursor: pointer;
}

.find-btn {
  margin-top: auto;
  padding: 14px;
  background: #2d2d2d;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  cursor: pointer;
  transition: background 0.2s;
}

.find-btn:hover {
  background: #444;
}
</style>
