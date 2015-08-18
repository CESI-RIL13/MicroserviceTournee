package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmplacementController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Emplacement.list(params), model:[emplacementCount: Emplacement.count()]
    }

    def show(Emplacement emplacement) {
        respond emplacement
    }

    def create() {
        respond new Emplacement(params)
    }

    @Transactional
    def save(Emplacement emplacement) {
        if (emplacement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emplacement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emplacement.errors, view:'create'
            return
        }

        emplacement.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emplacement.label', default: 'Emplacement'), emplacement.id])
                redirect emplacement
            }
            '*' { respond emplacement, [status: CREATED] }
        }
    }

    def edit(Emplacement emplacement) {
        respond emplacement
    }

    @Transactional
    def update(Emplacement emplacement) {
        if (emplacement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emplacement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emplacement.errors, view:'edit'
            return
        }

        emplacement.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'emplacement.label', default: 'Emplacement'), emplacement.id])
                redirect emplacement
            }
            '*'{ respond emplacement, [status: OK] }
        }
    }

    @Transactional
    def delete(Emplacement emplacement) {

        if (emplacement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emplacement.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'emplacement.label', default: 'Emplacement'), emplacement.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'emplacement.label', default: 'Emplacement'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
