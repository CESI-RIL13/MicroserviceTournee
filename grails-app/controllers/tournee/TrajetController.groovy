package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TrajetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Trajet.list(params), model:[trajetCount: Trajet.count()]
    }

    def show(Trajet trajet) {
        respond trajet
    }

    def create() {
        respond new Trajet(params)
    }

    @Transactional
    def save(Trajet trajet) {
        if (trajet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (trajet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond trajet.errors, view:'create'
            return
        }

        trajet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'trajet.label', default: 'Trajet'), trajet.id])
                redirect trajet
            }
            '*' { respond trajet, [status: CREATED] }
        }
    }

    def edit(Trajet trajet) {
        respond trajet
    }

    @Transactional
    def update(Trajet trajet) {
        if (trajet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (trajet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond trajet.errors, view:'edit'
            return
        }

        trajet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'trajet.label', default: 'Trajet'), trajet.id])
                redirect trajet
            }
            '*'{ respond trajet, [status: OK] }
        }
    }

    @Transactional
    def delete(Trajet trajet) {

        if (trajet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        trajet.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'trajet.label', default: 'Trajet'), trajet.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'trajet.label', default: 'Trajet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
