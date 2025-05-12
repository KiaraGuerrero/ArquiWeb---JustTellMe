package pe.edu.upc.demo3798api.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upc.demo3798api.entities.Reunion;
import pe.edu.upc.demo3798api.servicesinterfaces.IReunionService;

@Component("reunionAuth")
public class ReunionAuthorization {

    @Autowired
    private IReunionService reunionService;

    public boolean isPaciente(String username, Integer reunionId) {
        Reunion r = reunionService.listId(reunionId);
        return r != null && r.getPaciente().getUsername().equals(username);
    }

    public boolean isPsicologo(String username, Integer reunionId) {
        Reunion r = reunionService.listId(reunionId);
        return r != null && r.getPsicologo().getUsername().equals(username);
    }
}
