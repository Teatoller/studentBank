package studentbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studentbank.exception.StudentAlreadyExistException;
import studentbank.exception.StudentNotFoundException;
import studentbank.exception.StudentNotFoundException;
import studentbank.model.Student;
import studentbank.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService{
    private final StudentRepository studentRepository;
    @Override
    public Student addStudent(Student student) {
        if (studentAlreadyExist(student.getEmail())) {
            throw new StudentAlreadyExistException(student.getEmail() + " already exist");
        }
        return studentRepository.save(student);
    }

    private boolean studentAlreadyExist(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Student student, Long id) {
        return studentRepository.findById(id).map(selectStudent ->{
            selectStudent.setFirstName(student.getFirstName());
            selectStudent.setLastName(student.getLastName());
            selectStudent.setEmail(student.getEmail());
            selectStudent.setDepartment(student.getDepartment());
            return studentRepository.save(selectStudent);
        }).orElseThrow(()-> new StudentNotFoundException("Sorry, this student could not be found"));
    }

    @Override
    public Student getStudentById(Long id) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {

    }
}
