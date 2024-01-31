package org.halimatussyadiyah.pmb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// @JsonInclude(NON_DEFAULT)
public class Stats {
    private int totalBiodata;
    private int totalCard;
}
