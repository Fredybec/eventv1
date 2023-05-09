package ma.sir.event.bean.core;

import java.util.Objects;






import com.fasterxml.jackson.annotation.JsonInclude;
import ma.sir.event.zynerator.audit.AuditBusinessObject;
import javax.persistence.*;
import java.util.Objects;




@Entity
@Table(name = "evenement_state")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="evenement_state_seq",sequenceName="evenement_state_seq",allocationSize=1, initialValue = 1)
public class EvenementState   extends AuditBusinessObject     {

    private Long id;

    @Column(length = 500)
    private String reference;
    @Column(length = 500)
    private String code;



    public EvenementState(){
        super();
    }

    public EvenementState(Long id,String reference){
        this.id = id;
        this.reference = reference ;
    }




    @Id
    @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="evenement_state_seq")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }

    @Transient
    public String getLabel() {
        label = reference;
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvenementState evenementState = (EvenementState) o;
        return id != null && id.equals(evenementState.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

