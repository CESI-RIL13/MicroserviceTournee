package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AlveoleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Alveole.list(params), model:[alveoleCount: Alveole.count()]
    }

    def show(Alveole alveole) {
        respond alveole
    }

    def create() {
        respond new Alveole(params)
    }

    @Transactional
    def save(Alveole alveole) {
        if (alveole == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (alveole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond alveole.errors, view:'create'
            return
        }

        alveole.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'alveole.label', default: 'Alveole'), alveole.id])
                redirect alveole
            }
            '*' { respond alveole, [status: CREATED] }
        }
    }

    def edit(Alveole alveole) {
        respond alveole
    }

    @Transactional
    def update(Alveole alveole) {
        if (alveole == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (alveole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond alveole.errors, view:'edit'
            return
        }

        alveole.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'alveole.label', default: 'Alveole'), alveole.id])
                redirect alveole
            }
            '*'{ respond alveole, [status: OK] }
        }
    }

    @Transactional
    def delete(Alveole alveole) {

        if (alveole == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        alveole.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'alveole.label', default: 'Alveole'), alveole.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'alveole.label', default: 'Alveole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
