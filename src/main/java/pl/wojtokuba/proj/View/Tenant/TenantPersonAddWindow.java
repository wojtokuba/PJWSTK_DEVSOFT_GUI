package pl.wojtokuba.proj.View.Tenant;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import pl.wojtokuba.proj.Components.ComboBoxFormGroup;
import pl.wojtokuba.proj.Components.TextBoxFormGroup;
import pl.wojtokuba.proj.Model.Flat;
import pl.wojtokuba.proj.View.Developer.DeveloperMainWindow;
import pl.wojtokuba.proj.View.WindowRenderable;
import pl.wojtokuba.proj.ViewModel.Tenant.TenantPersonAddViewModel;

public class TenantPersonAddWindow extends TenantMainWindow implements WindowRenderable {

    TenantPersonAddViewModel tenantPersonAddViewModel;
    TextBoxFormGroup username;
    TextBoxFormGroup name;
    TextBoxFormGroup lastName;
    TextBoxFormGroup address;
    TextBoxFormGroup pesel;
    TextBoxFormGroup birthDate;
    ComboBoxFormGroup<Flat> flatList;
    Label errorMessage;

    @Override
    public void render() {
        tenantPersonAddViewModel = new TenantPersonAddViewModel(this);

        contentPanel.addComponent(new Label("NOWY WSPÓŁLOKATOR")
                .addStyle(SGR.BOLD)
                .setForegroundColor(TextColor.ANSI.BLUE)
                .setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.CENTER,
                        GridLayout.Alignment.BEGINNING,
                        true, false, 10, 1)
                )
        );
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(10)));

        contentPanel.addComponent(username = new TextBoxFormGroup("Nazwa użytkownika:",3,7));
        contentPanel.addComponent(name = new TextBoxFormGroup("Imię:",3,7));
        contentPanel.addComponent(lastName = new TextBoxFormGroup("Nazwisko:",3,7));
        contentPanel.addComponent(address = new TextBoxFormGroup("Adres:",3,7));
        contentPanel.addComponent(pesel = new TextBoxFormGroup("PESEL:",3,7));
        contentPanel.addComponent(birthDate = new TextBoxFormGroup("Data Urodzenia:",3,7));
        contentPanel.addComponent(
                flatList = new ComboBoxFormGroup<Flat>("Mieszkanie:", 4, 6)
                        .setValues(tenantPersonAddViewModel.getMyFlats())
        );
        contentPanel.addComponent( new Button("Zapisz", tenantPersonAddViewModel::create).setLayoutData(
                GridLayout.createLayoutData(
                        GridLayout.Alignment.CENTER,
                        GridLayout.Alignment.CENTER,
                        true,
                        false,
                        10,
                        1
                )));
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(10)));
        contentPanel.addComponent(errorMessage = new Label("").addStyle(SGR.BOLD)
                .setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.CENTER,
                        GridLayout.Alignment.CENTER,
                        true,
                        false,
                        10,
                        1)));
    }

    public String getUsername(){
        return this.username.getValue();
    }

    public Flat getSelectedFlat(){
        return this.flatList.getSelected();
    }

    public String getName(){
        return this.name.getValue();
    }

    public String getLastName(){
        return this.lastName.getValue();
    }

    public String getAddress(){
        return this.address.getValue();
    }

    public String getPesel(){
        return this.pesel.getValue();
    }

    public String getBirthDate(){
        return this.birthDate.getValue();
    }

    public void setErrorMessage(String text){
        this.errorMessage.setForegroundColor(new TextColor.RGB(255, 0, 0));
        this.errorMessage.setText(text);
    }

    public void success(){
        this.username.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.address.setValue("");
        this.pesel.setValue("");
        this.birthDate.setValue("");
        this.errorMessage.setForegroundColor(new TextColor.RGB(0, 255, 0));
        this.errorMessage.setText("Współlokator dodany pomyślnie!");
    }
}
