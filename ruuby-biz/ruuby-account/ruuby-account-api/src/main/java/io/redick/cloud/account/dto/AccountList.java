package io.redick.cloud.account.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Redick01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountList {

    private List<AccountDto> accounts;

    private int limit;

    private int offset;
}
