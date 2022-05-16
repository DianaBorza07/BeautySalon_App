package ro.sd.a2.utils;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PDFTextEditor {
    private TextFormatter textFormatter;

    public void generate(String text){
        textFormatter.formatText(text);
    }
}
