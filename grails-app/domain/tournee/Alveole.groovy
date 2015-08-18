package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Alveole {

    static belongsTo = [travee:Travee]
    static hasMany = [emplacements:Emplacement]

    static constraints = {
    }
}
