package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TraveeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Travee.list(params), model:[traveeCount: Travee.count()]
    }

    def show(Travee travee) {
        respond travee
    }

    def create() {
        respond new Travee(params)
    }

    @Transactional
    def save(Travee travee) {
        if (travee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (travee.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond travee.errors, view:'create'
            return
        }

        travee.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'travee.label', default: 'Travee'), travee.id])
                redirect travee
            }
            '*' { respond travee, [status: CREATED] }
        }
    }

    def edit(Travee travee) {
        respond travee
    }

    @Transactional
    def update(Travee travee) {
        if (travee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (travee.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond travee.errors, view:'edit'
            return
        }

        travee.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'travee.label', default: 'Travee'), travee.id])
                redirect travee
            }
            '*'{ respond travee, [status: OK] }
        }
    }

    @Transactional
    def delete(Travee travee) {

        if (travee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        travee.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'travee.label', default: 'Travee'), travee.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'travee.label', default: 'Travee'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
