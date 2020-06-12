import projectApi from '@/api/project'
export default {
  data: function () {
    return {
      entityModules: null
    }
  },
  methods: {
    findModules (queryString, cb) {
      const action = () => {
        const entityModules = this.entityModules.slice(0)
        const results = queryString ? entityModules.filter(
          c => c.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        ) : entityModules
        cb(results.map(c => ({ value: c })))
      }
      if (this.entityModules) {
        action()
      } else {
        projectApi.findModules(this.projectId)
          .then(data => {
            this.entityModules = data
            action()
          })
      }
    }
  }
}
