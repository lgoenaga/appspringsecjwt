package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.CaseServiceImpl;
import com.iudigital.appspringsecjwt.util.GeneralExceptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor
@Tag(name = "Cases", description = "API Case Management")
public class CaseController {

    Logger logger  = Logger.getLogger(CaseController.class.getName());

    final
    CaseServiceImpl caseService;

    GeneralExceptions generalExceptions= new GeneralExceptions();

    private String message;

    @Operation(
            summary = "Retrieve all Cases",
            description = "Get all cases.",
            tags = { "Cases", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "202", content = { @Content(schema = @Schema(implementation = CaseController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = CaseController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "402", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = CaseController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public ResponseEntity<Object> index(){
        try {
            List<CaseDtoResponse> caseDtoResponseList = caseService.getAll();
            logger.info(ConstantService.MODEL_CASE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(caseDtoResponseList);
        } catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {
        try {
            CaseDtoResponse caseDtoResponse = caseService.getCaseById(id);
            message = ConstantService.MODEL_CASE + " " + ConstantService.INFO_FOUND;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(caseDtoResponse);
        } catch (NullPointerExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getGeneralException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody CaseDtoRequest caseDtoRequest){
        try {
            CaseDtoResponse caseDtoResponse = caseService.saveCase(caseDtoRequest);
            message = ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(caseDtoResponse);
        } catch (IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody CaseDtoRequest caseDtoRequest) {
        try {

            CaseDtoResponse caseDtoResponse = caseService.updateCase(id, caseDtoRequest);
            message = ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(caseDtoResponse);
        } catch (NullPointerExceptions | IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Object> destroy(@PathVariable("id") Long id){
        try {
            caseService.deleteCase(id);
            message = ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        } catch (IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        }  catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

}
