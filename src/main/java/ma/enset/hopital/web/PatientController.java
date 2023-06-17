package ma.enset.hopital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.hopital.entities.Patient;
import ma.enset.hopital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor

public class PatientController {
   private PatientRepository patientRepository;
   @GetMapping("/index")
   @PreAuthorize("hasRole('USER')")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int p,
                        @RequestParam(name = "size",defaultValue = "4") int s,
                        @RequestParam(name = "keyword",defaultValue = "")String kw
   ){ /*on veut stocke la liste ds module*/
       //pr afficher une liste de patients
       Page<Patient> pagePatients=patientRepository.findByNomContains(kw,PageRequest.of(p,s));
       /*on veut stocke la liste ds module*/
       model.addAttribute("listPatients",pagePatients.getContent());
       //retourn nmbre total de page
       model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
       model.addAttribute("currentPage",p);
       model.addAttribute("keyword",kw);
       return "patients";
   }
   @GetMapping("/admin/delete")
   @PreAuthorize("hasRole('ADMIN')")
   public String delete(Long id,
                        @RequestParam(name="kayword",defaultValue = "") String keyword,
                        @RequestParam(name = "page",defaultValue = "0") int page){

       patientRepository.deleteById(id);
       return "redirect:/index?page="+page+"&keyword="+keyword;
   }
   //qd tape localhost8084 on affiche index
       @GetMapping("/")
       @PreAuthorize("hasRole('USER')")
       public String home(){

           return "redirect:/index"; }
    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ADMIN')")
    public String formPatients(Model model){
       model.addAttribute("patient",new Patient());
       return "formPatients";
    }
    @PostMapping("/admin/savepatient")
    @PreAuthorize("hasRole('ADMIN')")
    public  String savePatient(@Valid Patient patient, BindingResult bindingResult){
       if (bindingResult.hasErrors()){
           return "formPatients";
       }
      patientRepository.save(patient);
      return "redirect:/index?keyword="+patient.getNom();
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPatient(Model model, @RequestParam(name ="id") Long id){
       Patient patient=patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }
}
