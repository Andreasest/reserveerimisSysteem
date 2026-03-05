<template>
    <div class="filter-panel">
        <h2>Search for a Table</h2>
        <form @submit.prevent="submitSearch">
            <div class="form-group">
                <label for="numOfPeople">Number of People:</label>
                <input type="number" id="numOfPeople" v-model="filters.numberOfPeople" min="1" required />

                <label for="dateTime">Date and Time:</label>
                <input type="datetime-local" id="dateTime" v-model="filters.dateTime" required />


                
                <label for="zone">Zone:</label>
                <select id="zone" v-model="filters.zone">
                    <option value="">Select a zone</option>
                    <option value="MAIN">Main floor</option>
                    <option value="PRIVATE">Private room</option>
                    <option value="TERRACE">Terrace</option>
                </select>

                <div class="options">
                    <label for="window">Window seat:</label>
                    <input type="checkbox" id="window" v-model="filters.window" />
                    <label for="quiet">Quiet area:</label>
                    <input type="checkbox" id="quiet" v-model="filters.quiet" />
                </div>
                <button type="submit">Search</button>
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
                numberOfPeople: 1,
                zone: '',
                window: false,
                quiet: false,
                dateTime: '',
                durationHours: 1
            }
        };
    },
    methods: {
        submitSearch() {
            this.$emit('search', this.filters);
        }
    }
}
</script>