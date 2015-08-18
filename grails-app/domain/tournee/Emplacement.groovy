package tournee
import grails.rest.Resource

@Resource(formats=['html','json', 'xml'])
class Emplacement {

    Long longueur
    Long largeur
    Long volume
    Long poids_maxi
    Long ref_produit
    Long quantite
    Long duree_conservation

    static belongsTo = [alveole:Alveole]
    static constraints = {
    }
}
