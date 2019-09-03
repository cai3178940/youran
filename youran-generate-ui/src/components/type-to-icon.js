export default {
  map: {
    'xml': 'hawcons-icon-102-document-file-xml',
    'java': 'hawcons-icon-78-document-file-java',
    'yml': 'hawcons-icon-103-document-file-yml',
    'properties': 'hawcons-icon-48-notebook',
    'md': 'hawcons-icon-31-book-bookmark',
    'sql': 'hawcons-icon-89-document-file-sql'
  },
  getIcon (type) {
    const icon = this.map[type]
    if (icon) {
      return icon
    }
    return 'hawcons-icon-44-note-text'
  }
}
