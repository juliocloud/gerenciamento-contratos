package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.exceptions.ReferencedObjectDoesntExistException;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.repository.PartyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    private final ContractService contractService;

    @Autowired
    public PartyService(PartyRepository partyRepository, ContractService contractService) {
        this.partyRepository = partyRepository;
        this.contractService = contractService;
    }

    @Transactional
    public Party registerParty(Party party) {
        System.out.println(">>>>>>" + party.getContractId());
        if(partyRepository.existsById(party.getId())) {
            MakeFieldError fieldError = new MakeFieldError("id", "Duplicate party number");
            throw new DuplicateFieldValueException(fieldError);
        }

        if(!contractService.existsById(party.getContractId())){
            MakeFieldError fieldError = new MakeFieldError("contractId", "Contract doesn't exist in the database");
            throw new ReferencedObjectDoesntExistException(fieldError);
        }

        return partyRepository.save(party);
    }

    public List<Party> getPartiesAssociatedWithContract(String contractNumber){

        return partyRepository.findByContractNumber(contractNumber);
    }

    public Optional<Party> getById(String id){
        return partyRepository.findById(id);
    }

}
