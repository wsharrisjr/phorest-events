package com.phorest.events.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GenericData implements EventData {

    private Map<String, String> properties = new HashMap<>();


}
