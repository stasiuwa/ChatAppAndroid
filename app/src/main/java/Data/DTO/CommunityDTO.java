package Data.DTO;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDTO {
    private long id;
    private String name;
    private long ownerId;
    private UUID imageUrl;
    private int basePermissions;
}
