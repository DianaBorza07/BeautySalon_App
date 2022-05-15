package ro.sd.a2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.service.UserService;

import java.util.List;

@RestController
public class DummyController {

    @Autowired
    private UserService userService ;

    //@GetMapping("/home")
    public ResponseEntity<List<AppUserDTO>> getUsers(){
        List<AppUserDTO> userDTOList = userService.getAllUsers();
        if(userDTOList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(userDTOList);
    }
}
