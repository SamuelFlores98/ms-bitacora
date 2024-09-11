package mx.com.bitacora.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mx.com.bitacora.model.GenericResponse;
import mx.com.bitacora.model.Usuario;
import mx.com.bitacora.repository.UserRepository;
import mx.com.bitacora.utils.Constants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements IUserService{

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public GenericResponse<Usuario> insertUser(Usuario input) {
        Optional<Usuario> email = this.repository.findUserByEmail(input.getEmail());
        if (email.isEmpty()){
            input.setCreatedAt(String.valueOf(LocalDate.now()));
            return new GenericResponse<>(HttpStatus.CREATED, this.repository.save(input),
                    true, "Usuario Creado");
        }
        List<String> errors = new ArrayList<>();
        errors.add(Constants.EMAIL_DUPLICADO);

        return new GenericResponse<>(HttpStatus.CONFLICT, null, false, Constants.EMAIL_DUPLICADO);
    }

    @Override
    public List<Usuario> listUsers() {
        return this.repository.findAll();
    }

    @Override
    public GenericResponse<List<Usuario>> getListUsers() {
        List<Usuario> usuarios = this.repository.findAll();
        return new GenericResponse<>(HttpStatus.OK, usuarios, true, "Listado Exitoso");
    }

    @Override
    public GenericResponse<Usuario> updateUser(Long id, Usuario input) {
        Optional<Usuario> userExist = this.repository.findById(id);
        if (userExist.isPresent()) {
            userExist.get().setNombre(input.getNombre());
            userExist.get().setApellidoPaterno(input.getApellidoPaterno());
            userExist.get().setApellidoMaterno(input.getApellidoMaterno());
            userExist.get().setUpdatedAt(String.valueOf(LocalDate.now()));
            return new GenericResponse<>(HttpStatus.OK, this.repository.save(userExist.get()), true, "Actualizacion Exitosa");
        }
        return new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE);
    }

    @Override
    public GenericResponse<Usuario> deleteUser(Long id){
        boolean userExist = this.repository.existsById(id);
        if (!userExist) {
            return new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE);
        }
        this.repository.deleteById(id);
        return new GenericResponse<>(HttpStatus.OK, null, true, "Usuario Eliminado");
    }

    @Override
    public GenericResponse<Usuario> getUserById(Long id){
        Optional<Usuario> user = this.repository.findById(id);
        return user.map(usuario -> new GenericResponse<>(HttpStatus.OK, usuario, true, "Consulta Exitosa"))
                .orElseGet(() -> new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE));
    }

    @Override
    public GenericResponse<Usuario> login(String email) {
        Optional<Usuario> user = this.repository.findUserByEmail(email);
        return user.map(usuario -> new GenericResponse<>(HttpStatus.OK, usuario, true, "Login Exitoso"))
                .orElseGet(() ->
                        new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE));
    }

    @Override
    public GenericResponse<Map<String, Object>> exportUsers() {
        Map<String, Object> response = new HashMap<>();
        String[] columns = {"Id", "Nombre", "Apellido Paterno", "Apellido Materno"};
        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("Personas");
        Row row = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        List<Usuario> usuarios = this.listUsers();
        int initRow = 1;
        for (Usuario usuario : usuarios){
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(usuario.getIdUsuario());
            row.createCell(1).setCellValue(usuario.getNombre());
            row.createCell(2).setCellValue(usuario.getApellidoPaterno());
            row.createCell(3).setCellValue(usuario.getApellidoMaterno());
            initRow++;
        }
        try {
            workbook.write(stream);
            workbook.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("{}", Base64.getEncoder().encode(stream.toByteArray()));
        response.put("file", Base64.getEncoder().encode(stream.toByteArray()));
        return new GenericResponse<>(HttpStatus.OK, response, true, "Archivo Generado");
    }

    @Override
    public ByteArrayInputStream exportAllUsers() {
        String[] columns = {"Id", "Nombre", "Apellido Paterno", "Apellido Materno"};
        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("Personas");
        Row row = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        List<Usuario> usuarios = this.listUsers();
        int initRow = 1;
        for (Usuario usuario : usuarios){
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(usuario.getIdUsuario());
            row.createCell(1).setCellValue(usuario.getNombre());
            row.createCell(2).setCellValue(usuario.getApellidoPaterno());
            row.createCell(3).setCellValue(usuario.getApellidoMaterno());
            initRow++;
        }
        try {
            workbook.write(stream);
            workbook.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("{}", Base64.getEncoder().encode(stream.toByteArray()));
        return new ByteArrayInputStream(stream.toByteArray());
    }

    @Override
    public ICsvBeanWriter exportCsv(Writer writer) throws IOException {
        List<Usuario> usuarios = this.listUsers();
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
        String[] headers = {"Id", "Nombre", "Apellido Paterno", "Apellido Materno"};
        String[] namesMapping = {"idUsuario", "nombre", "apellidoPaterno", "apellidoMaterno"};

        csvBeanWriter.writeComment("No editar columna");
        csvBeanWriter.writeHeader(headers);
        for (Usuario user: usuarios) {
            csvBeanWriter.write(user,namesMapping);
        }
        return csvBeanWriter;
    }


}
