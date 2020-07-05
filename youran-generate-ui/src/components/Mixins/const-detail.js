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
        // 先预置空数组进去，让属性值先占上位置
        names.forEach(value => {
          this.constDetails[value] = []
        })
        return constApi.getDetailLists(
          {
            'projectId': projectId,
            'constName': names.join(',')
          })
          .then(data => {
            for (const name in data) {
              this.constDetails[name].push(...data[name])
            }
          })
      }
    }
  }
}
