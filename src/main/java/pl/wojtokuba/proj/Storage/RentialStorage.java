package pl.wojtokuba.proj.Storage;

import pl.wojtokuba.proj.Exceptions.TooManyRentialsException;
import pl.wojtokuba.proj.Model.Flat;
import pl.wojtokuba.proj.Model.Rential;
import pl.wojtokuba.proj.Model.User;
import pl.wojtokuba.proj.Utils.SimpleInjector;
import pl.wojtokuba.proj.Utils.TimeLapseManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class RentialStorage {
    private final HashMap<Integer, Rential> rential = new HashMap<>();

    public RentialStorage push(Rential rential) throws TooManyRentialsException {

        if(findAllActiveByUser(rential.getOwner()).size() + countParkingItemsActiveByUser(rential.getOwner()) > 5){
            throw new TooManyRentialsException();
        }
        this.rential.put(rential.getId(), rential);
        return this;
    }

    public Collection<Rential> findAll(){
        return new ArrayList<>(this.rential.values());
    }

    public Collection<Rential> findAllActiveFirst(){
        ArrayList<Rential> rentTable = new ArrayList<>(this.rential.values());
        Collections.sort(rentTable);
        return rentTable;
    }

    public Rential findOneActiveByFlat(Flat flat){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        for(Rential rential : this.rential.values()){
            if(rential.getFlat() == flat) {
                assert timeLapseManager != null;
                if (!rential.isArchived()) {
                    return rential;
                }
            }
        }
        return null;
    }

    public Rential findOneById(int id){
        for(Rential rential : this.rential.values()){
            if(rential.getId() == id) {
                return rential;
            }
        }
        return null;
    }


    public Collection<Rential> findAllActiveByUser(User user){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        Collection<Rential> result = new ArrayList<>();
        for(Rential rential : this.rential.values()){
            if(rential.getOwner() == user) {
                assert timeLapseManager != null;
                if (!rential.isArchived()) {
                    result.add(rential);
                }
            }
        }
        return result;
    }

    public Collection<Rential> findCalledForUser(User user){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        Collection<Rential> result = new ArrayList<>();
        for(Rential rential : this.rential.values()){
            if(rential.getOwner() == user) {
                assert timeLapseManager != null;
                if (rential.getPayCall() != null) {
                    result.add(rential);
                }
            }
        }
        return result;
    }

    public Collection<Rential> findNotArchivedForUser(User user){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        Collection<Rential> result = new ArrayList<>();
        for(Rential rential : this.rential.values()){
            if(rential.getOwner() == user) {
                assert timeLapseManager != null;
                if (!rential.isArchived()) {
                    result.add(rential);
                }
            }
        }
        return result;
    }

    public Collection<Rential> findAllActive(){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        Collection<Rential> result = new ArrayList<>();
        for(Rential rential : this.rential.values()){
            assert timeLapseManager != null;
            if (!rential.isArchived()) {
                result.add(rential);
            }
        }
        return result;
    }

    public Collection<Rential> findAllNotFinished(){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        Collection<Rential> result = new ArrayList<>();
        for(Rential rential : this.rential.values()){
            assert timeLapseManager != null;
            if (rential.getRentEnd().after(timeLapseManager.getAppDate())) {
                result.add(rential);
            }
        }
        return result;
    }

    public Collection<Rential> findFinishedNotArchived(){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        Collection<Rential> result = new ArrayList<>();
        for(Rential rential : this.rential.values()){
            assert timeLapseManager != null;
            if (!rential.isArchived() && rential.getRentEnd().before(timeLapseManager.getAppDate())) {
                result.add(rential);
            }
        }
        return result;
    }

    public int countParkingItemsActiveByUser(User user){
        TimeLapseManager timeLapseManager = (TimeLapseManager) SimpleInjector.resolveObject(TimeLapseManager.class);

        int result = 0;
        for(Rential rential : this.rential.values()){
            if(rential.getOwner() == user) {
                assert timeLapseManager != null;
                if (!rential.isArchived()) {
                    result += rential.isParkingRent() ? 1 : 0;
                }
            }
        }
        return result;
    }
}
