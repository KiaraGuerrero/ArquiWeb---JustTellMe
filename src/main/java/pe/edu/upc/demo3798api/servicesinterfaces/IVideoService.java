package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.Video;

import java.util.List;

public interface IVideoService {
    List<Video> list();
    Video insert(Video v);
    Video listId(Integer id);
    Video update(Video v);
    void delete(Integer id);
}
