<template>
  <div class="table-wrapper">
    <div class="chairs chairs-top">
      <div v-for="i in topChairs" :key="'top-' + i" class="chair" :class="tableStatusClass" />
    </div>

    <div class="table-item" :class="tableStatusClass" @click="$emit('select', table)">
      <span class="table-name">{{ table.name }}</span>
      <span class="table-capacity">{{ table.capacity }} people</span>
    </div>

    <div class="chairs chairs-bottom">
      <div v-for="i in bottomChairs" :key="'bottom-' + i" class="chair" :class="tableStatusClass" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'TableItem',
  props: {
    table: Object,
    recommended: Boolean,
    available: Boolean,
  },
  emits: ['select'],
  computed: {
    tableStatusClass() {
      // Determine the class based on the table's status
      if (this.recommended) return 'gold'
      if (this.available) return 'green'
      return 'gray'
    },
    topChairs() {
      // Calculate the number of chairs on the top side
      return Math.ceil(this.table.capacity / 2)
    },
    bottomChairs() {
      // Calculate the number of chairs on the bottom side
      return Math.floor(this.table.capacity / 2)
    },
  },
}
</script>

<style scoped>
.table-wrapper {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 4px;
  position: absolute;
  transform: translate(-50%, -50%);
  cursor: pointer;
}

.chairs {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.chair {
  width: 14px;
  height: 14px;
  border-radius: 3px;
  border: 2px solid rgba(0, 0, 0, 0.15);
}

.table-item {
  width: 60px;
  height: 80px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 4px;
  border: 2px solid rgba(0, 0, 0, 0.15);
  transition: transform 0.1s;
}

.table-item:hover {
  transform: scale(1.05);
}

.table-name {
  font-size: 13px;
  font-weight: 700;
  color: #fff;
}

.table-capacity {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.85);
}

.green {
  background-color: #4caf50;
  border-color: #388e3c;
}

.gray {
  background-color: #9e9e9e;
  border-color: #757575;
}

.gold {
  background-color: #ffc107;
  border-color: #f9a825;
}
</style>
