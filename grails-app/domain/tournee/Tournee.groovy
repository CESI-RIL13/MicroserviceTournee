package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Tournee {

    Long user
    Date debut
    Date fin
    Date prevision_fin

    static hasMany = [trajets:Trajet]

    static constraints = {
    }
}
