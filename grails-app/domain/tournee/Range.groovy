package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Range {

    static belongsTo = [alle:Alle]
    static hasMany = [travees:Travee]
    static constraints = {
    }
}
