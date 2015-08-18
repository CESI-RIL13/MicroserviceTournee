package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Trajet {

    Date debut_trajet
    Date fin_trajet
    Date prevision_fin
    String moyen_transport
    Emplacement depart
    Emplacement arrivee

    static belongsTo = [tournee:Tournee]

    static constraints = {
    }
}
