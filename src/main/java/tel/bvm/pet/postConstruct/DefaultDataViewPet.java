package tel.bvm.pet.postConstruct;

import org.springframework.stereotype.Component;
import tel.bvm.pet.model.ViewPet;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultDataViewPet {

    private final DefaultDataPet defaultDataPet;

    public DefaultDataViewPet(DefaultDataPet defaultDataPet) {
        this.defaultDataPet = defaultDataPet;
    }

    public ViewPet createViewPet(ViewPet.NameViewPet nameViewPet) {

        if (nameViewPet == ViewPet.NameViewPet.CAT) {
            ViewPet viewPet = new ViewPet(ViewPet.NameViewPet.CAT, null);
            viewPet.setId(2);
            return viewPet;
        } else if (nameViewPet == ViewPet.NameViewPet.DOG) {
            ViewPet viewPet = new ViewPet(ViewPet.NameViewPet.DOG, null);
            viewPet.setId(1);
            return viewPet;
        }
        throw new IllegalArgumentException("Unsupported view pet type: " + nameViewPet);
    }

//            if (nameViewPet.toString().equals("CAT")) {
//        ViewPet viewPet = new ViewPet(ViewPet.NameViewPet.CAT, null);
//        viewPet.setId(1);
//        return viewPet;
//    } else if (nameViewPet.toString().equals("DOG")) {
//        ViewPet viewPet = new ViewPet(ViewPet.NameViewPet.DOG, null);
//        viewPet.setId(2);
//        return viewPet;
//    }
//        throw new IllegalArgumentException("Unsupported view pet type: " + nameViewPet);



    Map<ViewPet.NameViewPet, ViewPet> viewPetMap = new HashMap<ViewPet.NameViewPet, ViewPet>(Map.of(
            createViewPet(ViewPet.NameViewPet.CAT).getNameViewPet(), createViewPet(ViewPet.NameViewPet.CAT),
            createViewPet(ViewPet.NameViewPet.DOG).getNameViewPet(), createViewPet(ViewPet.NameViewPet.DOG)));

    public void viewPetRegistry() {
        createViewPet(ViewPet.NameViewPet.CAT).setPets(defaultDataPet.petListViewPet(createViewPet(ViewPet.NameViewPet.CAT)));
        createViewPet(ViewPet.NameViewPet.DOG).setPets(defaultDataPet.petListViewPet(createViewPet(ViewPet.NameViewPet.DOG)));
    }

    public ViewPet viewPetInfo(ViewPet.NameViewPet nameViewPet) {
        return viewPetMap.get(nameViewPet);
    }
}
