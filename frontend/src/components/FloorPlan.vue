<template>
  <div class="floor-plan" ref="floorPlan">
    <!-- Legend -->
    <div class="legend">
      <div class="legend-item">
        <div class="legend-color green"></div>
        <span>Available</span>
      </div>
      <div class="legend-item">
        <div class="legend-color gold"></div>
        <span>Recommended</span>
      </div>
      <div class="legend-item">
        <div class="legend-color gray"></div>
        <span>Unavailable</span>
      </div>
    </div>

    <!-- Table items -->
    <TableItem
      v-for="table in tables"
      :key="table.id"
      :table="table"
      :recommended="isRecommended(table)"
      :available="isAvailable(table)"
      :style="tablePosition(table)"
      @select="selectTable(table)"
    />

    <!-- Filter out -->
    <div v-if="noFiltersMessage" class="no-filters-toast">Please select a date and time first!</div>

    <!-- Reservation popup -->
    <div v-if="selectedTable" class="popup-overlay" @click.self="closePopup">
      <div class="popup">
        <button class="popup-close" @click="closePopup">✕</button>
        <h3>Reserve {{ selectedTable.name }}</h3>
        <p class="popup-info">{{ selectedTable.capacity }} seats · {{ selectedTable.zone }}</p>

        <div class="popup-field">
          <label>Your Name</label>
          <input v-model="reservationName" type="text" placeholder="Enter your name" />
        </div>

        <div class="popup-field">
          <label>Date & Time</label>
          <p class="popup-value">{{ formattedDateTime }}</p>
        </div>

        <div class="popup-field">
          <label>Party Size</label>
          <p class="popup-value">{{ filters.numberOfPeople }} guests</p>
        </div>

        <div class="popup-field">
          <label>Duration</label>
          <p class="popup-value">2 hours</p>
        </div>

        <button class="popup-confirm" @click="confirmReservation" :disabled="!reservationName">
          Confirm Reservation
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import TableItem from './TableItem.vue'

export default {
  components: { TableItem },
  props: {
    tables: Array,
    recommended: Array,
    availableTables: Array,
    filters: Object,
  },
  data() {
    return {
      selectedTable: null,
      reservationName: '',
      noFiltersMessage: false,
    }
  },
  computed: {
    formattedDateTime() {
      // Formats date and time to display in the popup
      if (!this.filters?.dateTime) return 'N/A'
      return new Date(this.filters.dateTime).toLocaleString(undefined, {
        dateStyle: 'medium',
        timeStyle: 'short',
      })
    },
  },
  methods: {
    isRecommended(table) {
      // Checks if the table is in the recommended list
      return this.recommended.some((r) => r.id === table.id)
    },
    isAvailable(table) {
      // Checks if the table is available
      if (!this.availableTables?.length) return true
      return this.availableTables.includes(table.id)
    },
    selectTable(table) {
      // Selects a table for reservation
      if (!this.filters?.dateTime) {
        this.noFiltersMessage = true
        setTimeout(() => (this.noFiltersMessage = false), 3000)
        return
      }
      if (!this.isAvailable(table)) return
      this.selectedTable = table
      this.$emit('tableSelected', table)
    },
    tablePosition(table) {
      // Calculates the position of a table on the floor plan
      return {
        position: 'absolute',
        left: table.x + '%',
        top: table.y + '%',
      }
    },
    closePopup() {
      //Closes the reservation popup
      this.selectedTable = null
      this.reservationName = ''
    },
    confirmReservation() {
      // Confirms the reservation and sends it to the backend
      const dt = new Date(this.filters.dateTime + ':00')
      dt.setHours(dt.getHours() + 2)
      const end =
        dt.getFullYear() +
        '-' +
        String(dt.getMonth() + 1).padStart(2, '0') +
        '-' +
        String(dt.getDate()).padStart(2, '0') +
        'T' +
        String(dt.getHours()).padStart(2, '0') +
        ':' +
        String(dt.getMinutes()).padStart(2, '0') +
        ':00'
      const body = {
        name: this.reservationName,
        numberOfPeople: this.filters.numberOfPeople,
        table: { id: this.selectedTable.id },
        start: this.filters.dateTime + ':00',
        end: end,
      }

      fetch('http://localhost:8080/api/reservation', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      })
        .then((res) => res.json())
        .then(() => {
          this.closePopup()
          this.$emit('reservationMade')
          setTimeout(() => window.location.reload(), 500)
        })
        .catch((error) => console.error('Error making reservation:', error))
    },
  },
}
</script>

<style scoped>
.floor-plan {
  width: 100%;
  aspect-ratio: 16 / 9;
  max-height: 100%;
  max-width: calc(100vh * 16 / 9);
  position: relative;
  background-image: url(@/assets/background.png);
  background-size: cover;
  background-position: center;
  overflow: hidden;
}
.popup-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.popup {
  background: #fff;
  border-radius: 12px;
  padding: 28px 24px;
  width: 300px;
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 14px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
}

.popup-close {
  position: absolute;
  top: 12px;
  right: 12px;
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
  color: #888;
}

.popup h3 {
  font-size: 18px;
  font-weight: 700;
  color: #2c2c2c;
  margin: 0;
}

.popup-info {
  font-size: 13px;
  color: #888;
  margin: 0;
}

.popup-field {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.popup-field label {
  font-size: 12px;
  font-weight: 600;
  color: #888;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.popup-field input {
  padding: 8px 12px;
  border-radius: 8px;
  border: 1px solid #ddd;
  font-size: 14px;
  color: #2c2c2c;
  outline: none;
}

.popup-field input:focus {
  border-color: #2c2c2c;
}

.popup-value {
  font-size: 14px;
  color: #2c2c2c;
  margin: 0;
}

.popup-confirm {
  padding: 12px;
  background: #2c2c2c;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  cursor: pointer;
  transition: background 0.2s;
  margin-top: 4px;
}

.popup-confirm:hover:not(:disabled) {
  background: #444;
}

.popup-confirm:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.no-filters-toast {
  position: absolute;
  top: 16px;
  left: 50%;
  transform: translateX(-50%);
  background: #2c2c2c;
  color: #fff;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 14px;
  z-index: 20;
}
.legend {
  position: absolute;
  top: 16px;
  right: 16px;
  background: rgba(255, 255, 255, 0.9);
  padding: 8px 12px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.legend-item {
  display: flex;
  align-items: center;
  gap: 6px;
}
.legend-color {
  width: 14px;
  height: 14px;
  border-radius: 3px;
}
.legend-color.green {
  background-color: #4caf50;
  border: 2px solid #388e3c;
}
.legend-color.gold {
  background-color: #ffeb3b;
  border: 2px solid #fbc02d;
}
.legend-color.gray {
  background-color: #9e9e9e;
  border: 2px solid #757575;
}
</style>
