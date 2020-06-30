import constApi from '@/api/const'
export default {
  data: function () {
    return {
      constDetails: {}
    }
  },
  methods: {
    loadConstDetail (projectId, constName) {
      if (!constName) {
        return Promise.resolve()
      }
      if (this.constDetails[constName]) {
        return Promise.resolve()
      } else {
        return constApi.getDetailList(
          {
            'projectId': projectId,
            'constName': constName
          })
          .then(data => {
            this.constDetails[constName] = data
          })
      }
    }
  }
}
