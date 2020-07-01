import constApi from '@/api/const'
export default {
  data: function () {
    return {
      constDetails: {}
    }
  },
  methods: {
    loadConstDetail (projectId, ...constName) {
      const names = constName.filter(value => !this.constDetails[value])
      if (!names.length) {
        return Promise.resolve()
      } else {
        return constApi.getDetailLists(
          {
            'projectId': projectId,
            'constName': names.join(',')
          })
          .then(data => {
            Object.assign(this.constDetails, data)
          })
      }
    }
  }
}
