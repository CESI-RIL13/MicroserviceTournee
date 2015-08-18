package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Travee {

    static belongsTo = [range:Range]
    static hasMany = [alveoles:Alveole]

    static constraints = {
    }
}
