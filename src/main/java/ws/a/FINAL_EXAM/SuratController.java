/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.FINAL_EXAM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Magaretha Wahyunda Syahputri
 * NIM : 20200140002
 */
@RestController
@CrossOrigin
public class SuratController {
    
    Surat srt = new Surat();
    SuratJpaController ctrl = new SuratJpaController();
    
    //List<Daftarbarang> barangList = new ArrayList<Daftarbarang>();
    
    @GetMapping(value = "/GET", produces = APPLICATION_JSON_VALUE)//Menampilkan data
    public List<Surat> getAll(){
        try{
            List<Surat> listsurat = new ArrayList<>();
            listsurat = ctrl.findSuratEntities();
            
            return listsurat; //return data jika data tersedia
        } catch (Exception e) {
            return List.of(); //data tidak tersedia
        }
    }
    
    @PostMapping(value = "/POST", consumes = APPLICATION_JSON_VALUE)//Menambahkan data
    public String postSurat(HttpEntity<String> kirimsurat) throws JsonProcessingException//mengambil data dari tabel daftarbarang
    {
        String feedback = "Do Nothing";
        ObjectMapper mapper =new ObjectMapper();
        srt = mapper.readValue(kirimsurat.getBody(), Surat.class);
        try{
            ctrl.create(srt);
            feedback = srt.getJudul() + " Saved";
        }catch(Exception e){
            feedback = e.getMessage();
        }
        return feedback;
    }
    
    
    //Mengedit data
    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String putSurat(HttpEntity<String> kirimsurat) throws JsonProcessingException {
        
        String feedback = "Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        srt = mapper.readValue(kirimsurat.getBody(), Surat.class);
        try{
            ctrl.edit(srt);
            feedback = srt.getJudul() + " Edited";
        } catch (Exception e){
            feedback = e.getMessage();
        }
        
        return feedback;
    }
    
    //Menghapus data  
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    public String deleteSurat(HttpEntity<String> kirimsurat) throws JsonProcessingException{
        
        String feedback = "Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        srt = mapper.readValue(kirimsurat.getBody(), Surat.class);
        try{
            ctrl.destroy(srt.getId());
            feedback = "Data has been Deleted";
        }catch (Exception e){
            feedback = e.getMessage();
        }
        
        return feedback;
    }
    
}
