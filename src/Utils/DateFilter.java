/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import io.github.palexdev.materialfx.beans.BiPredicateBean;
import io.github.palexdev.materialfx.filter.base.AbstractFilter;
import io.github.palexdev.materialfx.i18n.I18N;
import io.github.palexdev.materialfx.utils.FXCollectors;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

public class DateFilter<T> extends AbstractFilter<T, Date> {

    public DateFilter(String name, Function<T, Date> extractor) {
        this(name, extractor, new DateStringConverter(Locale.ENGLISH, "yyyy-MM-dd"));
    }

    public DateFilter(String name, Function<T, Date> extractor, StringConverter<Date> converter) {
        super(name, extractor, converter);
    }

    @Override
    protected ObservableList<BiPredicateBean<Date, Date>> defaultPredicates() {
        return Stream.<BiPredicateBean<Date, Date>>of(
                new BiPredicateBean<>(I18N.getOrDefault("filter.is"), (aDate, aDate2) -> aDate.compareTo(aDate2) == 0),
                new BiPredicateBean<>(I18N.getOrDefault("filter.isNot"), (aDate, aDate2) -> aDate.compareTo(aDate2) != 0),
                new BiPredicateBean<>(I18N.getOrDefault("filter.greater"), (aDate, aDate2) -> aDate.compareTo(aDate2) > 0),
                new BiPredicateBean<>(I18N.getOrDefault("filter.greaterEqual"), (aDate, aDate2) -> aDate.compareTo(aDate2) >= 0),
                new BiPredicateBean<>(I18N.getOrDefault("filter.lesser"), (aDate, aDate2) -> aDate.compareTo(aDate2) < 0),
                new BiPredicateBean<>(I18N.getOrDefault("filter.lesserEqual"), (aDate, aDate2) -> aDate.compareTo(aDate2) <= 0)
        ).collect(FXCollectors.toList());
    }

    @Override
    protected DateFilter<T> extend(BiPredicateBean<Date, Date>... predicateBeans) {
        Collections.addAll(super.predicates, predicateBeans);
        return this;
    }

}
