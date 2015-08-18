package tournee

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RangeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Range.list(params), model:[rangeCount: Range.count()]
    }

    def show(Range range) {
        respond range
    }

    def create() {
        respond new Range(params)
    }

    @Transactional
    def save(Range range) {
        if (range == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (range.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond range.errors, view:'create'
            return
        }

        range.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'range.label', default: 'Range'), range.id])
                redirect range
            }
            '*' { respond range, [status: CREATED] }
        }
    }

    def edit(Range range) {
        respond range
    }

    @Transactional
    def update(Range range) {
        if (range == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (range.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond range.errors, view:'edit'
            return
        }

        range.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'range.label', default: 'Range'), range.id])
                redirect range
            }
            '*'{ respond range, [status: OK] }
        }
    }

    @Transactional
    def delete(Range range) {

        if (range == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        range.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'range.label', default: 'Range'), range.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'range.label', default: 'Range'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
