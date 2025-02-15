package Philately.stamp.service;

import Philately.stamp.model.Stamp;
import Philately.stamp.model.WishedStamp;
import Philately.stamp.repository.StampRepository;
import Philately.stamp.repository.WishedStampRepository;
import Philately.user.model.User;
import Philately.web.dto.CreateNewStamp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StampService {

    private final StampRepository stampRepository;
    private final WishedStampRepository wishedStampRepository;

    public StampService(StampRepository stampRepository, WishedStampRepository wishedStampRepository) {
        this.stampRepository = stampRepository;
        this.wishedStampRepository = wishedStampRepository;
    }

    public void create(CreateNewStamp createNewStamp, User user){

        Stamp stamp = Stamp.builder()
                .imageUrl(createNewStamp.getImageUrl())
                .name(createNewStamp.getName())
                .description(createNewStamp.getDescription())
                .paper(createNewStamp.getPaper())
                .owner(user)
                .build();

        stampRepository.save(stamp);

    }

    public List<Stamp> getAll() {

        return stampRepository.findAll();
    }

    public void createWished(UUID stampId, User user) {

        Stamp stamp = getById(stampId);

        boolean isAlreadyWished = user.getWishedStamps()
                        .stream()
                       .anyMatch(ws -> ws.getName().equals(stamp.getName()) && ws.getDescription().equals(stamp.getDescription()));
                if(isAlreadyWished){
                    return;
                }

        WishedStamp wishedStamp = WishedStamp.builder()
                .imageUrl(stamp.getImageUrl())
                .name(stamp.getName())
                .description(stamp.getDescription())
                .owner(user)
                .build();

        wishedStampRepository.save(wishedStamp);
    }

    public Stamp getById(UUID id){

        return stampRepository.findById(id).orElseThrow(() -> new RuntimeException("Stamp with id [%s] does not exist!".formatted(id)));
    }

    public void deleteWishedStampById(UUID id) {

        wishedStampRepository.deleteById(id);
    }
}
