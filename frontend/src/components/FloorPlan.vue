<template>
  <div class="floor-plan">
    <div class="zone" v-for="zone in zones" :key="zone">
      <h3>{{ zone }}</h3>
      <div class="zone-grid">
        <TableItem
          v-for="table in getTablesForZone(zone)"
          :key="table.id"
          :table="table"
          :recommended="isRecommended(table)"
          @click="selectTable(table)"
        />
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
    recommended: Array
  },
  data() {
    return {
      zones: ['MAIN', 'TERRACE', 'PRIVATE']
    }
  },
  methods: {
    getTablesForZone(zone) {
      return this.tables.filter(t => t.zone === zone)
    },
    isRecommended(table) {
      return this.recommended.some(r => r.id === table.id)
    },
    selectTable(table) {
      this.$emit('tableSelected', table)
    }
  }
}
</script>