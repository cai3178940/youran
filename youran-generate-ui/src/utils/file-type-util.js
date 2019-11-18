export default {
  iconMap: {
    'xml': 'xml',
    'ftl': 'code',
    'java': 'java',
    'yml': 'yaml',
    'properties': 'properties',
    'md': 'md',
    'sql': 'sql',
    'folder': 'folder',
    'txt': 'txt'
  },
  cmMode: {
    'xml': 'application/xml',
    'ftl': 'application/xml',
    'java': 'text/x-java',
    'yml': 'text/x-yaml',
    'properties': 'text/x-properties',
    'md': 'text/x-markdown',
    'sql': 'text/x-mysql'
  },
  getIcon (type) {
    const icon = this.iconMap[type]
    if (icon) {
      return icon
    }
    return this.iconMap['txt']
  },
  getCmMode (type) {
    const mode = this.cmMode[type]
    if (mode) {
      return mode
    }
    return ''
  }
}
