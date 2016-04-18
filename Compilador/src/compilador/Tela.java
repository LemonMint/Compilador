/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lemon
 */
public class Tela extends javax.swing.JFrame {

    /**
     * Creates new form Tela
     */
    public Tela() throws IOException {
        initComponents();
        MenuSair.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        MenuManual.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(jMenu1, "Manual ainda não implementado");
            }
        });

        MenuAtualizar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(jMenu1, "Nenhuma atualização disponível");
            }
        });

        MenuCreditos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(jMenu1, "Implementado por:\nMarcos Paulo\nRaul Porto");
            }
        });
        MenuAbrir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("Akali (*.akali)", "akali");
                fc.setFileFilter(xmlFilter);
                //Exibe o diálogo. Deve ser passado por parâmetro o JFrame de origem.
                fc.showOpenDialog(txtArea);
                //Captura o objeto File que representa o arquivo selecionado.
                File selFile = fc.getSelectedFile();
                txtArea.setText(lerArquivo(selFile.getAbsolutePath()));

            }
        });

        MenuNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
            }
        });

        btLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                String colunas[] = new String[]{"Linha", "Token", "Código"};
                jTable1.setModel(new DefaultTableModel(colunas, 0));
            }
        });

        MenuSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*       JFileChooser salvandoArquivo = new JFileChooser();
                
                 int resultado = salvandoArquivo.showSaveDialog(null);
                 if (resultado == JFileChooser.APPROVE_OPTION) {
                 File salvarArquivoEscolhido = salvandoArquivo.getSelectedFile();
                 try {
                 OutputStream os = new FileOutputStream(salvarArquivoEscolhido);
                 os.write(txtArea.getText().getBytes());
                 os.close();
                 } catch (IOException ex) {
                 Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
                 }*/
                JFileChooser fc = new JFileChooser();
                fc.showSaveDialog(txtArea);
                if (fc.getSelectedFile().getName().endsWith(".akali") || fc.getSelectedFile().getName().endsWith(".txt")) {
                    gravarArquivo(fc.getSelectedFile().getAbsolutePath(), txtArea.getText());
                } else {
                    gravarArquivo(fc.getSelectedFile().getAbsolutePath() + ".akali", txtArea.getText());
                }
            }
        }
        );

        MenuCompilar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                FileReader fileReader = null;
                BufferedReader bufferedReader = null;
                gravarArquivo("temp.txt", txtArea.getText());
                final String dir = System.getProperty("user.dir");
                String aux = dir + "\\temp.txt";
                System.out.println("current dir = " + aux);
                ArrayList<RetornoLexico> retornoLexico = analisar.analisarLinha(aux);
                String colunas[] = new String[]{"Linha", "Token", "Código"};
                DefaultTableModel defaultTableModel = new DefaultTableModel(colunas, retornoLexico.size());
                int linha = 0;
                for (RetornoLexico rl : retornoLexico) {
                    defaultTableModel.setValueAt(rl.LinhaArquivo, linha, 0);
                    defaultTableModel.setValueAt(rl.Token, linha, 1);
                    defaultTableModel.setValueAt(rl.Codigo, linha, 2);
                    linha++;
                }
                jTable1.setModel(defaultTableModel);
            }
        });

        BtCompilar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                jTable1.removeAll();
                FileReader fileReader = null;
                BufferedReader bufferedReader = null;
                gravarArquivo("temp.txt", txtArea.getText());
                final String dir = System.getProperty("user.dir");
                String aux = dir + "\\temp.txt";
                System.out.println("current dir = " + aux);
                ArrayList<RetornoLexico> retornoLexico = analisar.analisarLinha(aux);
                String colunas[] = new String[]{"Linha", "Token", "Código"};
                DefaultTableModel defaultTableModel = new DefaultTableModel(colunas, retornoLexico.size());
                int linha = 0;
                for (RetornoLexico rl : retornoLexico) {
                    defaultTableModel.setValueAt(rl.LinhaArquivo, linha, 0);
                    defaultTableModel.setValueAt(rl.Token, linha, 1);
                    defaultTableModel.setValueAt(rl.Codigo, linha, 2);
                    linha++;
                }
                jTable1.setModel(defaultTableModel);
            }
        });
    }

    private String lerArquivo(String nomeArquivo) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(nomeArquivo);
            bufferedReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            while (bufferedReader.ready()) {
                sb.append(bufferedReader.readLine());
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: "
                    + ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: "
                            + ex.getMessage());
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: "
                            + ex.getMessage());
                }
            }
        }
        return null;
    }

    private void gravarArquivo(String nomeArquivo, String textoArquivo) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(nomeArquivo, false);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(textoArquivo);
            bufferedWriter.flush();
            //Se chegou ate essa linha, conseguiu salvar o arquivo com sucesso.
            JOptionPane.showMessageDialog(this, "Salvo com sucesso");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + ex.getMessage());
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: "
                            + ex.getMessage());
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: "
                            + ex.getMessage());
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BtCompilar = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuNovo = new javax.swing.JMenuItem();
        MenuAbrir = new javax.swing.JMenuItem();
        MenuSalvar = new javax.swing.JMenuItem();
        MenuSair = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuCompilar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        MenuManual = new javax.swing.JMenuItem();
        MenuCreditos = new javax.swing.JMenuItem();
        MenuAtualizar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linha", "Token", "Código"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        BtCompilar.setText("Compilar");
        BtCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCompilarActionPerformed(evt);
            }
        });

        btLimpar.setText("Limpar");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        jMenu1.setText("Arquivo");

        MenuNovo.setText("Novo");
        jMenu1.add(MenuNovo);

        MenuAbrir.setText("Abrir");
        MenuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(MenuAbrir);

        MenuSalvar.setText("Salvar");
        jMenu1.add(MenuSalvar);

        MenuSair.setText("Sair");
        jMenu1.add(MenuSair);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Executar");

        MenuCompilar.setText("Compilar");
        jMenu2.add(MenuCompilar);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ajuda");

        MenuManual.setText("Manual");
        jMenu3.add(MenuManual);

        MenuCreditos.setText("Créditos");
        jMenu3.add(MenuCreditos);

        MenuAtualizar.setText("Atualizar");
        jMenu3.add(MenuAtualizar);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BtCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtCompilar)
                    .addComponent(btLimpar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAbrirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuAbrirActionPerformed

    private void BtCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCompilarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtCompilarActionPerformed

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btLimparActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Tela().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCompilar;
    private javax.swing.JMenuItem MenuAbrir;
    private javax.swing.JMenuItem MenuAtualizar;
    private javax.swing.JMenuItem MenuCompilar;
    private javax.swing.JMenuItem MenuCreditos;
    private javax.swing.JMenuItem MenuManual;
    private javax.swing.JMenuItem MenuNovo;
    private javax.swing.JMenuItem MenuSair;
    private javax.swing.JMenuItem MenuSalvar;
    private javax.swing.JButton btLimpar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea txtArea;
    // End of variables declaration//GEN-END:variables
}
