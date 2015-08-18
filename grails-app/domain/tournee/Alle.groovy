package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Alle {

    static hasMany = [ranges:Range]
    static constraints = {
    }
}
