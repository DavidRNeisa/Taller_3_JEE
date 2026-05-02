param(
    [switch]$ForceInstall
)

function Has-Command($n){ return (Get-Command $n -ErrorAction SilentlyContinue) -ne $null }

Write-Host "Comprobando dotnet..."
if (Has-Command dotnet -and -not $ForceInstall) {
    Write-Host "dotnet detectado. Ejecutando servicio..."
    cd (Split-Path -Path $MyInvocation.MyCommand.Definition -Parent)
    dotnet restore
    dotnet run --urls "http://localhost:5000"
    exit $LASTEXITCODE
}

if (Has-Command winget) {
    Write-Host "Instalando .NET 7 SDK via winget..."
    winget install --id Microsoft.DotNet.SDK.7 -e --silent
    if ($LASTEXITCODE -ne 0) { Write-Host "winget falló. Abriendo página de descarga."; Start-Process "https://dotnet.microsoft.com/en-us/download/dotnet/7.0"; exit 1 }
    Write-Host "Instalación completada. Cierra y reabre la terminal si es necesario. Intentando ejecutar..."
    Start-Sleep -s 2
    cd (Split-Path -Path $MyInvocation.MyCommand.Definition -Parent)
    dotnet restore
    dotnet run --urls "http://localhost:5000"
    exit $LASTEXITCODE
} else {
    Write-Host "No se encontró 'winget'. Abriendo la página oficial para descargar .NET 7 SDK.";
    Start-Process "https://dotnet.microsoft.com/en-us/download/dotnet/7.0"
    exit 1
}
