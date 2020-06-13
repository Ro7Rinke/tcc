const {execSync} = require('child_process')
const fs = require('fs')


let params = {
    algorithm: 'aes',//nome do algoritmo
    key: 128,//tamanho da chave
    data: '50',//nome do arquivo q contem a string a ser criptografada
    run: 10,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
}

let results = {
    cryptTime: null,
    decryptTime: null,
    cryptSize: null,
    cryptMemory: null,
    decryptMemory: null,
    keyTime: null
}

const ReadResults = (i) => {
    let data
    
    data = fs.readFileSync(`./data/singleresults/${params.data}-${params.algorithm}-${params.key}-sizeCrypt-${i}.dat`)
    if(results.cryptSize == null)
        results.cryptSize = parseInt(data)
    else
        results.cryptSize = (parseInt(data) + results.cryptSize) / 2

    data = fs.readFileSync(`./data/singleresults/${params.data}-${params.algorithm}-${params.key}-usedTimeCrypt-${i}.dat`)
    if(results.cryptTime == null)
        results.cryptTime = parseInt(data)
    else
        results.cryptTime = (parseInt(data) + results.cryptTime) / 2

    data = fs.readFileSync(`./data/singleresults/${params.data}-${params.algorithm}-${params.key}-usedTimeDecrypt-${i}.dat`)
    if(results.decryptTime == null)
        results.decryptTime = parseInt(data)
    else
        results.decryptTime = (parseInt(data) + results.decryptTime) / 2

    data = fs.readFileSync(`./data/singleresults/${params.data}-${params.algorithm}-${params.key}-useMemoryCrypt-${i}.dat`)
    if(results.cryptMemory == null)
        results.cryptMemory = parseInt(data)
    else
        results.cryptMemory = (parseInt(data) + results.cryptMemory) / 2

    data = fs.readFileSync(`./data/singleresults/${params.data}-${params.algorithm}-${params.key}-useMemoryDecrypt-${i}.dat`)
    if(results.decryptMemory == null)
        results.decryptMemory = parseInt(data)
    else
        results.decryptMemory = (parseInt(data) + results.decryptMemory) / 2

    data = fs.readFileSync(`./data/singleresults/${params.data}-${params.algorithm}-${params.key}-usedTimeKey-${i}.dat`)
    if(results.keyTime == null)
        results.keyTime = parseInt(data)
    else
        results.keyTime = (parseInt(data) + results.keyTime) / 2
}

const main = () => {

    // execSync('del "./data/" /f /q /s')
    
    for(let i = 0; i < params.run; i++){
        execSync(`cd ./data && java -jar ../dist/ClientSocket.jar ${params.data} ${params.algorithm} ${params.key} ${i}`)
        ReadResults(i)
    }
    
    for(let i in results)
        results[i] = Math.round(results[i])

    fs.writeFileSync(`./data/${params.data}-${params.algorithm}-${params.key}-results.dat`, JSON.stringify(results))
}

main()

