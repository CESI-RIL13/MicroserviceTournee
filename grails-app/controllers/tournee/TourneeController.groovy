package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TourneeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tournee.list(params), model:[tourneeCount: Tournee.count()]
    }

    def show(Tournee tournee) {
        respond tournee
    }

    def create() {
        respond new Tournee(params)
    }

    @Transactional
    def save(Tournee tournee) {
        if (tournee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tournee.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tournee.errors, view:'create'
            return
        }

        tournee.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tournee.label', default: 'Tournee'), tournee.id])
                redirect tournee
            }
            '*' { respond tournee, [status: CREATED] }
        }
    }

    def edit(Tournee tournee) {
        respond tournee
    }

    @Transactional
    def update(Tournee tournee) {
        if (tournee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tournee.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tournee.errors, view:'edit'
            return
        }

        tournee.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tournee.label', default: 'Tournee'), tournee.id])
                redirect tournee
            }
            '*'{ respond tournee, [status: OK] }
        }
    }

    @Transactional
    def delete(Tournee tournee) {

        if (tournee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tournee.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tournee.label', default: 'Tournee'), tournee.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tournee.label', default: 'Tournee'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
