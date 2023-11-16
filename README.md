[![SAM Deploy](https://github.com/HauVu94/PGR301-DevOps-Eksamen/actions/workflows/deply.yml/badge.svg)](https://github.com/HauVu94/PGR301-DevOps-Eksamen/actions/workflows/deply.yml)


## Her har jeg laget en check liste for meg selv. Besvarelse for oppgaven kommer etter checklisten

# Eksamens oppgaver sjekkliste

## Oppgave 1 Kjell's Python kode - 20 Poeng
- [x] Oppgave A - SAM & GitHub actions workflow
- [ ] Oppgave B - Docker container

## Oppgave 2 Overgang fra Java og Spring boot - 15 poeng
- [ ] Oppgave A - Dockerfile
- [ ] Oppgave B - GitHub Action workflow for container image og ECR

## Oppgave 3 - Terraform, AWS Apprunner og Infrastruktur som kode - 15 poeng
- [ ] Oppgave A - Kodeendringer og forbedringer
- [ ] Oppgave B - Terraform i GitHub Actions

## Oppgave 4 - Feedback - 30 poeng
- [ ] Oppgave A - Utvid applikasjonen og legg inn "Måleinstrumenter"
- [ ] Oppgave B - CloudWatch Alarm og Terraform moduler

## Oppgave 5 - Drøfteoppgaver - 20 poeng
- [ ] Oppgave A - Kontinuelig Integrering

- [ ] Oppgave B - Sammenligning av Scrum/Smidig og DevOps fra et Utviklers Perspektiv 
    - [ ] Oppgave 1 - Scrum/Smidig Metodikk
    - [ ] Oppgave 2 - DevOps Metodikk
    - [ ] Oppgave 3 - Sammenligning og Kontrast
  
- [ ] Oppgave C - Det Andre Prinsippet Feedback 


# Besvarelse oppgave 1

Først må du/sensor Forke repositoryen.
Hvis du går inn på din terminal og skriver inn: git clone https://github.com/<DIN GITHUB BRUKER>/PGR301-DevOps-Eksamen.git
Etter at du har fått forket er det viktig å lage Github Secrets i repositiriet.
    1. gå inn på settings
    2. under Security finner du Secrets and variables, gå til Actions
    3. Her må du lage 2 secrets, den ene kaller du for AWS_ACESS_KEY_ID og den andre AWS_SECRET_ACESS_KEY. Du kan finne disse kodene inne på AWS og IAM, da vil du knytte AWS kontoen din til Github
    4. Du trenger å gjøre en endring i '.github/workflows/deply.yml' for å kunne pushe opp til github.
    5. i terminal i rotmappen kan du skrive "git add .", deretter 'git commit -m "skriv en commit melding" ', deretter git push. 
    6. Github Actions vil da kjøre, hvis alt funker vil den kjøre grønt :)
    
    

    
    

# Besvarelse oppgave 5

Oppgave A



