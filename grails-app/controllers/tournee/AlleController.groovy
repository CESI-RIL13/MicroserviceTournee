package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AlleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Alle.list(params), model:[alleCount: Alle.count()]
    }

    def show(Alle alle) {
        respond alle
    }

    def create() {
        respond new Alle(params)
    }

    @Transactional
    def save(Alle alle) {
        if (alle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (alle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond alle.errors, view:'create'
            return
        }

        alle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'alle.label', default: 'Alle'), alle.id])
                redirect alle
            }
            '*' { respond alle, [status: CREATED] }
        }
    }

    def edit(Alle alle) {
        respond alle
    }

    @Transactional
    def update(Alle alle) {
        if (alle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (alle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond alle.errors, view:'edit'
            return
        }

        alle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'alle.label', default: 'Alle'), alle.id])
                redirect alle
            }
            '*'{ respond alle, [status: OK] }
        }
    }

    @Transactional
    def delete(Alle alle) {

        if (alle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        alle.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'alle.label', default: 'Alle'), alle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'alle.label', default: 'Alle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
